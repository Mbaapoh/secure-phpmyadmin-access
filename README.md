# DevSecOps Reference Architecture Showcase

Welcome to the Fintech DevSecOps Showcase Repository! This repository is organized using a **Strictly Modular Composable Architecture**. 

Every DevSecOps tool (Jenkins, Microcks, Keycloak, etc.) is its own completely independent showcase. You can mix and match these modules to build your perfect platform!

## 📁 Repository Structure

Inside every module, you will find a clean split:
* `/ansible/`: The Infrastructure as Code to deploy the tool.
* `/project/`: (If applicable) The source code, API specs, or Jenkinsfiles that run on that tool.

### [00-foundational-platform](./00-foundational-platform)
The "Master Project". It provides Edge Routing (Traefik) and Centralized Identity (Keycloak). **Start here.** All other showcases plug into this master network and SSO realm.

### [01-secure-access-gateway](./01-secure-access-gateway)
Demonstrates how to use an Identity Aware Proxy (OAuth2-Proxy) to protect legacy, public-facing applications (like phpMyAdmin) without touching their code.

### [02-microcks-api-mocking](./02-microcks-api-mocking)
Demonstrates Stateful API Mocking. Contains the OpenAPI specs in the `project/` folder.

### [03-jenkins-cicd-platform](./03-jenkins-cicd-platform)
Demonstrates a central Jenkins CI/CD platform using Configuration as Code (JCasC). You can run the pipeline (located in the `project/` folder) to sync the Microcks APIs! This will also house the Jenkins Shared Library.

### [04-sonarqube-inspection](./04-sonarqube-inspection)
*(Coming Soon)* Continuous code quality and security scanning module.

## 🚀 How to Use
To run a showcase:
1. `cd` into the showcase's `ansible` directory (e.g., `cd 01-secure-access-gateway/ansible`).
2. Review the `inventory/` and `group_vars/` configurations.
3. Run the playbook locally:
   ```bash
   ansible-playbook playbooks/<playbook_name>.yml --vault-password-file ../../.vault_pass
   ```
