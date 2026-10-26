# Database Setup Order

Run each SQL file in MySQL Workbench:
1. `StudentDBApp/database/setup.sql`
2. `LoginDBApp/database/setup.sql`
3. `EmployeeDBApp/database/setup.sql`

Each script creates its own database and table. The login script inserts the lab account `admin` / `1234`.

Replace `YOUR_MYSQL_PASSWORD` in each database Java file with the password for your local MySQL account. Do not put real credentials in a public repository.
