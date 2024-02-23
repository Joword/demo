<template>
    <div class="question-card-outer">
        <div style="flex: 10"></div>
        <div style="flex: 16">
            <div class="question-card">
                <div v-html="$t(props.info)"></div>
            </div>
        </div>
        <div style="width: 20px;"></div>
    </div>
</template>

<script setup lang="ts">
    const props = defineProps(['info'])
    console.log(props.info)
</script>

<style scoped lang="css">
    .question-card-outer {
        display: flex;
        text-align: right;
        background-color: white;
        padding: 20px 0;
    }
    .question-card {
        border: 1px solid #1F5294;
        min-height: 20px;
        padding: 5px;
        border-top-left-radius: 8px;
        border-top-right-radius: 8px;
        border-bottom-left-radius: 8px;
        max-width: 500px;
        background-color: #1F5294;
        color: aliceblue;
        text-align: left;
    }
</style>