
<script setup>
import { ref, onMounted } from "vue";
import { api } from "../services/api";

const employees = ref([]);
const leaves = ref([]);
const message = ref("");
const filterType = ref("");
const filterStatus = ref("");
const loadingEmployees = ref(false);

const form = ref({
  employeeId: "",
  leaveType: "Sick",
  startDate: "",
  endDate: ""
});

async function loadEmployees() {
  loadingEmployees.value = true;

  try {
    const data = await api.getEmployees();
    employees.value = Array.isArray(data) ? data : [];

    if (
      form.value.employeeId &&
      !employees.value.some(
        e => e.employeeId === form.value.employeeId
      )
    ) {
      form.value.employeeId = "";
    }

    console.log("Employees loaded:", employees.value);
  } catch (error) {
    console.error("Could not load employees:", error);
    message.value =
      "Could not load employees. Check that Spring Boot is running.";
  } finally {
    loadingEmployees.value = false;
  }
}

async function loadLeaves() {
  try {
    const data = await api.getLeaves(
      filterType.value,
      filterStatus.value
    );
    leaves.value = Array.isArray(data) ? data : [];
  } catch (error) {
    console.error("Could not load leaves:", error);
    message.value = "Could not load leave requests: " + error.message;
  }
}

async function refreshAll() {
  await Promise.all([loadEmployees(), loadLeaves()]);
}

async function applyLeave() {
  message.value = "";

  if (!form.value.employeeId) {
    message.value = "Please select an employee.";
    return;
  }

  if (!form.value.startDate || !form.value.endDate) {
    message.value = "Please select both dates.";
    return;
  }

  if (form.value.endDate < form.value.startDate) {
    message.value = "End date cannot be before start date.";
    return;
  }

  try {
    const result = await api.applyLeave({ ...form.value });

    message.value = `Request processed: ${result.status}. Leave days: ${result.numberOfDays}.`;

    await refreshAll();
    window.dispatchEvent(new Event("leave-data-changed"));

    form.value.startDate = "";
    form.value.endDate = "";
  } catch (error) {
    message.value = "Failed to apply: " + error.message;
  }
}

async function updateStatus(leave, status) {
  try {
    await api.updateLeaveStatus(leave.leaveId, status);
    message.value = `Leave request updated to ${status}.`;

    await refreshAll();
    window.dispatchEvent(new Event("leave-data-changed"));
  } catch (error) {
    message.value = "Status update failed: " + error.message;
  }
}

function employeeName(id) {
  const employee = employees.value.find(
    e => String(e.employeeId) === String(id)
  );

  return employee?.employeeName || `Employee ${id}`;
}

onMounted(refreshAll);
</script>

<template>
  <section class="panel">
    <h2>Leave Management</h2>

    <p v-if="message" role="status">{{ message }}</p>

    <form @submit.prevent="applyLeave" class="form-grid">
      <label>
        Employee
        <select v-model="form.employeeId" required>
          <option value="" disabled>
            {{ loadingEmployees ? "Loading employees..." :
               employees.length ? "Select employee" : "No employees found" }}
          </option>

          <option
            v-for="employee in employees"
            :key="employee.employeeId"
            :value="employee.employeeId"
          >
            {{ employee.employeeName || "Unnamed Employee" }}
            ({{ employee.employeeId }})
          </option>
        </select>
      </label>

      <label>
        Leave Type
        <select v-model="form.leaveType" required>
          <option value="Sick">Sick Leave</option>
          <option value="Regular">Regular Leave</option>
          <option value="Casual">Casual Leave</option>
        </select>
      </label>

      <label>
        Start Date
        <input v-model="form.startDate" type="date" required />
      </label>

      <label>
        End Date
        <input v-model="form.endDate" type="date" required />
      </label>

      <button type="submit" :disabled="loadingEmployees || !employees.length">
        Apply for Leave
      </button>
    </form>

    <div class="heading-row">
      <h3>All Leave Requests</h3>
      <button class="secondary" type="button" @click="refreshAll">
        Refresh
      </button>
    </div>

    <div class="filter-row">
      <select v-model="filterType" @change="loadLeaves">
        <option value="">All Types</option>
        <option value="Sick">Sick</option>
        <option value="Regular">Regular</option>
        <option value="Casual">Casual</option>
      </select>

      <select v-model="filterStatus" @change="loadLeaves">
        <option value="">All Statuses</option>
        <option value="Accepted">Accepted</option>
        <option value="Rejected">Rejected</option>
        <option value="Cancelled">Cancelled</option>
      </select>
    </div>

    <div class="table-wrapper">
      <table>
        <thead>
          <tr>
            <th>Leave ID</th>
            <th>Employee</th>
            <th>Type</th>
            <th>Dates</th>
            <th>Days</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="leave in leaves" :key="leave.leaveId">
            <td>{{ leave.leaveId }}</td>
            <td>{{ employeeName(leave.employeeId) }}</td>
            <td>
              <span :class="['type-tag', (leave.leaveType || '').toLowerCase()]">
                {{ leave.leaveType }}
              </span>
            </td>
            <td>{{ leave.startDate }} – {{ leave.endDate }}</td>
            <td>{{ leave.numberOfDays }}</td>
            <td>
              <span :class="['status-tag', (leave.status || '').toLowerCase()]">
                {{ leave.status }}
              </span>
            </td>
            <td>
              <button
                v-if="leave.status === 'Accepted'"
                class="danger small"
                type="button"
                @click="updateStatus(leave, 'Rejected')"
              >
                Reject
              </button>

              <button
                v-if="leave.status === 'Cancelled'"
                class="secondary small"
                type="button"
                @click="updateStatus(leave, 'Accepted')"
              >
                Accept
              </button>
            </td>
          </tr>

          <tr v-if="leaves.length === 0">
            <td colspan="7">No leave requests found.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>
