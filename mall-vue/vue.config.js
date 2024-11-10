module.exports = {
    devServer: {
        //关闭vue的保护机制，让外网能访问
        disableHostCheck: true,
            port: 8080,
            proxy: {
                '/api': {
                    //要访问的跨域的域名
                    target: 'http://localhost:8081/',
                    ws: true, // 是否启用websockets
                    //开启代理：在本地会创建一个虚拟服务端，然后发送请求的数据，并同时接收请求的数据，这样客户端端和服务端进行数据的交互就不会有跨域问题
                    changOrigin: true,
                    pathRewrite: {
                        '^/api': ''
                    }
                }
        }
    }
}