# DevSecOps Reference Architecture Showcase

Welcome to the Fintech DevSecOps Showcase Repository! This repository demonstrates a highly scalable, modular infrastructure. It features a **Centralized Ansible Control Plane** governing isolated application projects.

## 📁 Repository Structure

### `/ansible` (The Control Plane)
Contains all infrastructure-as-code for the platform. This serves as the single source of truth for your entire cluster.
* `/ansible/inventory/`: The unified inventory file.
* `/ansible/group_vars/`: The unified secrets and variables.
* `/ansible/roles/`: All isolated roles (`deploy_traefik`, `deploy_keycloak`, `deploy_microcks`, `deploy_jenkins`, etc.).
* `/ansible/playbooks/`: Individual deployment playbooks. 

### `/projects` (The Application Plane)
Contains the code, pipelines, and specs that run *on* the deployed infrastructure.
* `/projects/api-mocking/`: Contains the OpenAPI specs for Microcks.
* `/projects/jenkins-cicd/`: Contains Jenkinsfiles and your Jenkins Shared Library.
* `/projects/secure-phpmyadmin-access/`: An isolated demo project showing how to protect a legacy web app with OAuth2-Proxy.

## 🚀 How to Deploy
1. Navigate to the `ansible` directory:
   ```bash
   cd ansible
   ```
2. Run any playbook you need! To spin up the foundational routing and identity layer:
   ```bash
   ansible-playbook playbooks/00-deploy_traefik.yml --vault-password-file ../.vault_pass
   ansible-playbook playbooks/01-deploy_keycloak.yml --vault-password-file ../.vault_pass
   ```
3. To spin up specific tool platforms:
   ```bash
   ansible-playbook playbooks/02-deploy_jenkins.yml --vault-password-file ../.vault_pass
   ansible-playbook playbooks/03-deploy_microcks.yml --vault-password-file ../.vault_pass
   ansible-playbook playbooks/04-deploy_oauth2proxy.yml --vault-password-file ../.vault_pass
   ansible-playbook playbooks/05-deploy_pma.yml --vault-password-file ../.vault_pass
   ```
