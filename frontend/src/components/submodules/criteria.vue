<template>
    <!-- <div style="border: 0.15rem solid #A6A6A6;border-radius: 5px;height: 750px;">
        <Paper :size="'530px'"></Paper>
    </div> -->
    <!-- <n-scrollbar style="height: 1130px">
        <Paper></Paper>
    </n-scrollbar> -->
    <div>
        <Paper></Paper>
    </div>
</template>

<script setup lang="ts">
    import Paper from "./paper.vue";
</script>

<style lang="css">
    .n-divider__line {
        background-color: #a6a6a6;
    }
    #disease .n-checkbox-box__border {
        color: #f1ca17;
    }
    .disease-style {
        margin-right: 5px;
        background-color:#f1ca17;
        cursor: pointer;
        border-radius: 5px;
        border: 1px solid #f1ca17;
        height: 50px;
    }
    .gene-style {
        margin-right: 5px;
        background-color:#983680;
        cursor: pointer;
        border-radius: 5px;
        border: 1px solid #983680;
        height: 50px;
    }
    .mutation-style {
        margin-right: 5px;
        background-color:#57c3c2;
        cursor: pointer;
        border-radius: 5px;
        border: 1px solid #57c3c2;
        height: 50px;
    }
</style>