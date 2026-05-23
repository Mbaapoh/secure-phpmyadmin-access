# Manage Keycloak User Role

This role allows you to quickly add, delete, or reset the OTP for a user in Keycloak using Ansible ad-hoc variables. 

## Usage Examples

Run these commands from the root directory (`/Users/macuser/mbaapoh-devops/secure-phpmyadmin-access`).

### 1. Add a New User
To add a new user and force them to setup Google Authenticator (OTP) on their first login:
```bash
ansible-playbook playbooks/manage_user.yml -i inventory/hosts.ini --vault-password-file .vault_pass \
  -e "target_username=johndoe target_password=SuperSecret123! target_email=johndoe@demo.okay.cm target_firstname=John target_lastname=Doe"
```

### 2. Reset a User's OTP
If a user loses their phone or gets stuck on the OTP code screen, you can reset them. By setting `reset_otp=true`, Ansible will delete the user and immediately recreate them, restoring the QR Code setup screen on their next login.
```bash
ansible-playbook playbooks/manage_user.yml -i inventory/hosts.ini --vault-password-file .vault_pass \
  -e "target_username=mbaapoh target_password=MbaapohPassword237! reset_otp=true"
```

### 3. Delete a User
To completely remove a user from the system:
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
| `reset_otp` | `false` | If `true`, deletes the user before recreating them to reset the Google Authenticator requirement |
| `target_state` | `present` | Use `absent` to delete the user entirely |
