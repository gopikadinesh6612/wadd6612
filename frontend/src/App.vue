<script setup>
import EmployeeManagement from "./components/EmployeeManagement.vue";
import LeaveManagement from "./components/LeaveManagement.vue";
import LeaveAnalytics from "./components/LeaveAnalytics.vue";
</script>

<template>
  <header class="header">
    <h1>Employee Leave Management System</h1>
    <p>Manage employees, leave requests and analytics</p>
  </header>

  <main class="container">
    <EmployeeManagement />
    <LeaveManagement />
    <LeaveAnalytics />
  </main>
</template>