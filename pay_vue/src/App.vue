<script setup lang="ts">
/**
 * @type import('naive-ui').GlobalThemeOverrides
 */
import {zhCN,dateZhCN } from 'naive-ui'
const themeOverrides = {
    // common: {
    //     primaryColor: '#096dd9',
    //     boxShadow1:'#ff0000'
    // }
    // Button: {
    //     textColor: '#FF0000'
    // },
    // Select: {
    //     peers: {
    //         InternalSelection: {
    //             textColor: '#FF0000'
    //         }
    //     }
    // }
}

</script>

<template>
    <n-config-provider :theme-overrides="themeOverrides" :locale="zhCN" :date-locale="dateZhCN">
        <n-dialog-provider>
            <n-message-provider>
                <router-view/>
            </n-message-provider>
        </n-dialog-provider>
    </n-config-provider>
</template>

<style>
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}

.app-logo {
    width: 120px;
}
.code-content{
    background-color: #1e212d;
    color:#fafafa;
    padding:10px;
    border-radius: 4px;
}
.code-content pre{
    font-family: inherit;
    margin: 0;
}
</style>
