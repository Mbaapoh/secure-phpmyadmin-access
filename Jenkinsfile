pipeline {
    agent any
    stages {
        stage('Sync API Contracts') {
            steps {
                microcksImport(
                    server: 'microcks-cloud',
                    specificationFiles: 'api-specs/mtn-momo-collections-v1.yaml:true'
                )
                microcksImport(
                    server: 'microcks-cloud',
                    specificationFiles: 'api-specs/cart-api.yaml:true'
                )
            }
        }
    }
}
