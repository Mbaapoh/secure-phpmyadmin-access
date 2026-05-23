# DevSecOps Reference Architecture Showcase

Welcome to the Fintech DevSecOps Showcase Repository! This repository is organized using a **Composable Architecture**. Instead of one massive monolithic playbook, the infrastructure is broken down into independent showcases. 

You can mix and match these showcases to build your platform, but they all build upon the `00-foundational-platform`.

## 📁 Repository Structure

### [00-foundational-platform](./00-foundational-platform)
The core infrastructure that provides Edge Routing (Traefik) and Centralized Identity (Keycloak). **Start here.** All other showcases assume this platform is running.

### [01-secure-access-gateway](./01-secure-access-gateway)
Demonstrates how to use an Identity Aware Proxy (OAuth2-Proxy) to protect legacy, public-facing applications (like phpMyAdmin) without touching their code.

### [02-api-mocking-gitops](./02-api-mocking-gitops)
Demonstrates a fully automated, GitOps-driven Stateful API Mocking pipeline using Jenkins and Microcks. 

### [03-jenkins-shared-library](./03-jenkins-shared-library)
*(Coming Soon)* A repository for reusable DevSecOps compliance and security pipeline scripts.

## 🚀 How to Use
To run a showcase:
1. `cd` into the showcase directory (e.g., `cd 01-secure-access-gateway`).
2. Review the `inventory/` and `group_vars/` configurations.
3. Run the playbook locally:
   ```bash
   ansible-playbook playbooks/<playbook_name>.yml --vault-password-file ../.vault_pass
   ```
