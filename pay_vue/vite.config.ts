import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
// @ts-ignore
import commonjs from 'rollup-plugin-commonjs'
import externalGlobals from 'rollup-plugin-external-globals'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    server: {
        port: 3000
    },
    build:{
        rollupOptions:{
            external:['vue','moment','axios'],
            plugins: [
                commonjs(),
                externalGlobals({
                    vue: "Vue",
                    moment: "moment",
                    axios: "axios",
                }),
            ],
        }
    }
})
