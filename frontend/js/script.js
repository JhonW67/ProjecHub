if ( 'serviceWorker' in navigator){
    navigator.serviceWorker.register("/frontend/js/sw.js")
    .then(() => console.log("✅ Conexão com o serviceWorker estabelecida ✅"))
    .catch( (error) => console.log("❌ Conexão com o serviceWorker falha ❌", error))
}