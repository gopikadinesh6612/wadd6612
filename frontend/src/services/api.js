const BASE_URL = "http://localhost:8080";

async function request(url, options = {}) {
  const response = await fetch(`${BASE_URL}${url}`, {
    headers: {
      "Content-Type": "application/json",
      ...options.headers
    },
    ...options
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || `Request failed: ${response.status}`);
  }

  if (response.status === 204) return null;

  const text = await response.text();
  return text ? JSON.parse(text) : null;
}

export const api = {
  getEmployees: () => request("/employees"),

  getEmployee: id =>
    request(`/employees/${encodeURIComponent(id)}`),

  addEmployee: employee =>
    request("/employees", {
      method: "POST",
      body: JSON.stringify(employee)
    }),

  getLeaves: (type = "", status = "", employeeId = "") => {
    const params = new URLSearchParams();

    if (type) params.set("type", type);
    if (status) params.set("status", status);
    if (employeeId) params.set("employeeId", employeeId);

    const query = params.toString();
    return request(`/leaves${query ? `?${query}` : ""}`);
  },

  applyLeave: leave =>
    request("/leaves", {
      method: "POST",
      body: JSON.stringify(leave)
    }),

  updateLeaveStatus: (id, status) =>
    request(
      `/leaves/${encodeURIComponent(id)}/status?status=${encodeURIComponent(status)}`,
      { method: "PATCH" }
    ),

  countByType: () =>
    request("/leaves/analytics/count-by-type"),

  countByTypeAndStatus: () =>
    request("/leaves/analytics/type-status"),

  employeeLeaveDays: () =>
    request("/leaves/analytics/employee-leave-days")
};