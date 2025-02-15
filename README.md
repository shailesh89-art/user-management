# user-management

## Important Setup Step: Add Roles Before Registering Users

###  Before testing the API, you must insert roles into the database manually.**
 If you skip this step, **the application will throw an error** when registering a user because it expects roles to exist in the database.

---

## **Step 1: Insert Roles in Database**
Before creating users, insert the required roles (`ADMIN` and `USER`) into the `roles` table.

### **Run the following SQL queries:**
```sql
INSERT INTO role (name) VALUES ('ADMIN');
INSERT INTO role (name) VALUES ('USER');
