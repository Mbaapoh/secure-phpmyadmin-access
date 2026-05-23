# Manage Keycloak User Role

This role allows you to quickly add, delete, or reset the OTP for a user in Keycloak using Ansible ad-hoc variables. 

## Usage Examples

Run these commands from the root directory (`/Users/macuser/mbaapoh-devops/secure-phpmyadmin-access`).

### 1. Declarative Approach (Recommended)
You can define a list of users in your `group_vars/all/vars.yml` and the role will automatically create and enforce them. This is the true GitOps approach.

Add this list to your variables:
```yaml
keycloak_users:
  - username: "alice"
    password: "AlicePassword123!"
    email: "alice@demo.okay.cm"
    firstname: "Alice"
    lastname: "Smith"
    require_otp: true
    state: present
  - username: "bob_service_account"
    password: "BobPassword123!"
    require_otp: false
```
Then simply run:
```bash
ansible-playbook playbooks/manage_user.yml -i inventory/hosts.ini --vault-password-file .vault_pass
```

---

### 2. Imperative Approach (Ad-hoc Command Line)
If you don't want to save the user in code, you can pass variables directly on the command line.

**Add a New User (Requires them to set up Google Authenticator):**
```bash
ansible-playbook playbooks/manage_user.yml -i inventory/hosts.ini --vault-password-file .vault_pass \
  -e "target_username=johndoe target_password=SuperSecret123! target_email=johndoe@demo.okay.cm target_firstname=John target_lastname=Doe"
```

**Add a User Without OTP:**
If you want to create a service account or simply don't want MFA for a specific user:
```bash
ansible-playbook playbooks/manage_user.yml -i inventory/hosts.ini --vault-password-file .vault_pass \
  -e "target_username=no_mfa_user target_password=SuperSecret123! require_otp=false"
```

**Reset an Existing User's OTP:**
*(If someone loses their phone, this safely deletes and recreates them, restoring the QR Code prompt).*
```bash
ansible-playbook playbooks/manage_user.yml -i inventory/hosts.ini --vault-password-file .vault_pass \
  -e "target_username=mbaapoh target_password=NewPassword237! reset_otp=true"
```

**Delete a User:**
```bash
ansible-playbook playbooks/manage_user.yml -i inventory/hosts.ini --vault-password-file .vault_pass \
  -e "target_username=johndoe target_state=absent"
```

## Variables

| Variable | Default | Description |
|---|---|---|
| `target_username` | **Required** | The username to manage |
| `target_password` | *Optional* | The password to set for the user (Required if creating a new user) |
| `target_email` | `<username>@demo.okay.cm` | The email for the user |
| `target_firstname` | `<username>` | The first name |
| `target_lastname` | `User` | The last name |
| `require_otp` | `true` | If `true`, forces the user to configure Google Authenticator on their next login |
| `reset_otp` | `false` | If `true`, deletes the user before recreating them to reset the Google Authenticator requirement |
| `target_state` | `present` | Use `absent` to delete the user entirely |
