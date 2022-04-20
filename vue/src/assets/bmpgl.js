// bmpgl.js
export function BMPGL(ak) {
    return new Promise(function(resolve, reject) {
        window.init = function() {
            // eslint-disable-next-line
            resolve(BMapGL)
        }
        const script = document.createElement('script')
        script.type = 'text/javascript'
        script.src = `http://api.map.baidu.com/reverse_geocoding/v3/?ak=${ak}&callback=init`
        script.onerror = reject
        document.head.appendChild(script)
    })
}