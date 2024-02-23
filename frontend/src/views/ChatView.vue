<template>
    <n-space vertical>
        <n-layout>
            <n-layout-header>
                <Header></Header>
            </n-layout-header>
            <n-layout-content>
                <Chat></Chat>
            </n-layout-content>
          <!--<n-layout-footer><FooterStay></FooterStay></n-layout-footer>-->
        </n-layout>
    </n-space>
</template>

<script setup lang="ts">
    import Header from "../components/header.vue";
    import Chat from "../components/chat.vue";
    //import FooterStay from "../components/footer-stay.vue";
</script>