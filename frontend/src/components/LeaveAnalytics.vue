<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import { api } from "../services/api";

const typeCounts = ref([]);
const statusCounts = ref([]);
const employeeDays = ref([]);
const message = ref("");

async function loadAnalytics() {
  try {
    const [types, statuses, days] = await Promise.all([
      api.countByType(),
      api.countByTypeAndStatus(),
      api.employeeLeaveDays()
    ]);

    console.log("Type counts:", types);
    console.log("Status counts:", statuses);
    console.log("Employee leave days:", days);

    typeCounts.value = types || [];
    statusCounts.value = statuses || [];
    employeeDays.value = days || [];
    message.value = "";
  } catch (error) {
    console.error("Analytics failed:", error);
    message.value = "Analytics error: " + error.message;
  }
}

onMounted(() => {
  loadAnalytics();
  window.addEventListener("leave-data-changed", loadAnalytics);
});

onUnmounted(() => {
  window.removeEventListener("leave-data-changed", loadAnalytics);
});
</script>

<template>
  <section class="panel">
    <div class="heading-row">
      <h2>Leave Analytics Dashboard</h2>
      <button class="secondary" @click="loadAnalytics">
        Refresh Analytics
      </button>
    </div>

    <p v-if="message">{{ message }}</p>

    <h3>Number of Requests by Leave Type</h3>

    <div class="analytics-grid">
      <article
        v-for="item in typeCounts"
        :key="item.leaveType"
        :class="['analytics-card', item.leaveType.toLowerCase()]">
        <h3>{{ item.leaveType }} Leave</h3>
        <p class="big-number">{{ item.totalRequests }}</p>
        <span>Total Requests</span>
      </article>
    </div>

    <h3>Requests by Leave Type and Status</h3>

    <div class="table-wrapper">
      <table>
        <thead>
          <tr>
            <th>Leave Type</th>
            <th>Status</th>
            <th>Total Requests</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in statusCounts"
              :key="`${item.leaveType}-${item.status}-${index}`">
            <td>{{ item.leaveType }}</td>
            <td>{{ item.status }}</td>
            <td>{{ item.totalRequests }}</td>
          </tr>
          <tr v-if="statusCounts.length === 0">
            <td colspan="3">No statistics available.</td>
          </tr>
        </tbody>
      </table>
    </div>

    <h3>Total Accepted Leave Days by Employee</h3>

    <div class="table-wrapper">
      <table>
        <thead>
          <tr>
            <th>Employee ID</th>
            <th>Employee Name</th>
            <th>Total Accepted Leave Days</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in employeeDays" :key="item.employeeId">
            <td>{{ item.employeeId }}</td>
            <td>{{ item.employeeName || "Unknown" }}</td>
            <td>{{ item.totalAcceptedLeaveDays }}</td>
          </tr>
          <tr v-if="employeeDays.length === 0">
            <td colspan="3">No accepted leave days available.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>