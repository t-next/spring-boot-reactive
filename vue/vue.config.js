devServer: {
    proxy: {
      "/api": {
        ws: true,
        changeOrigin: true,
        target: "http://localhost:8080"
      }
    }
}