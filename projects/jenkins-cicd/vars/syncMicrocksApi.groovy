def call(Map config = [:]) {
    def server = config.server ?: 'microcks-cloud'
    def specs = config.specs
    
    if (!specs) {
        error "syncMicrocksApi: 'specs' parameter is required!"
    }
    
    echo "Syncing API Contracts to Microcks (${server}): ${specs}"
    
    microcksImport(
        server: server,
        specificationFiles: specs
    )
}
