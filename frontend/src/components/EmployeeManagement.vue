<script setup>
import { ref, onMounted } from "vue";
import { api } from "../services/api";

const employees = ref([]);
const message = ref("");

const form = ref({
  employeeId: "",
  employeeName: "",
  sickLeaveBalance: 10,
  regularLeaveBalance: 12,
  casualLeaveBalance: 8
});

async function loadEmployees() {
  try {
    employees.value = await api.getEmployees();
  } catch (error) {
    message.value = error.message;
  }
}

async function addEmployee() {
  try {
    await api.addEmployee({ ...form.value });
    message.value = "Employee added successfully!";

    form.value = {
      employeeId: "",
      employeeName: "",
      sickLeaveBalance: 10,
      regularLeaveBalance: 12,
      casualLeaveBalance: 8
    };

    await loadEmployees();
  } catch (error) {
    message.value = error.message;
  }
}

onMounted(loadEmployees);
</script>

<template>
  <section class="panel">
    <h2>Employee Management</h2>

    <form @submit.prevent="addEmployee" class="form-grid">
      <input v-model.trim="form.employeeId"
             placeholder="Employee ID" required />

      <input v-model.trim="form.employeeName"
             placeholder="Employee Name" required />

      <label>
        Sick Leave Balance
        <input v-model.number="form.sickLeaveBalance"
               type="number" min="0" required />
      </label>

      <label>
        Regular Leave Balance
        <input v-model.number="form.regularLeaveBalance"
               type="number" min="0" required />
      </label>

      <label>
        Casual Leave Balance
        <input v-model.number="form.casualLeaveBalance"
               type="number" min="0" required />
      </label>

      <button type="submit">Add Employee</button>
    </form>

    <p v-if="message">{{ message }}</p>

    <h3>All Employees</h3>

    <div class="employee-grid">
      <article v-for="employee in employees"
               :key="employee.employeeId"
               class="employee-card">
        <h3>{{ employee.employeeName }}</h3>
        <p>ID: {{ employee.employeeId }}</p>

        <div class="balance sick">
          Sick Leave: {{ employee.sickLeaveBalance }}
        </div>

        <div class="balance regular">
          Regular Leave: {{ employee.regularLeaveBalance }}
        </div>

        <div class="balance casual">
          Casual Leave: {{ employee.casualLeaveBalance }}
        </div>
      </article>
    </div>

    <button class="secondary" @click="loadEmployees">
      Refresh Employees
    </button>
  </section>
</template>