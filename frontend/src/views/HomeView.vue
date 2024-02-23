<template>
    <n-space vertical>
        <n-layout>
            <n-layout-content>
                <Home></Home>
            </n-layout-content>
            <n-layout-footer style="background-color: white;">
                <Footer></Footer>
            </n-layout-footer>
        </n-layout>
    </n-space>
</template>

<script setup lang="ts">
    import Footer from "../components/footer.vue";
    import Home from "../components/home.vue";
    
</script>

<style lang="css">
    
</style>