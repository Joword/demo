<template>
    <div class="answer-card-outer">
        <div style="width: 20px;"></div>
        <div style="flex:16">
            <div class="answer-card">
                <div v-html="$t(props.info)"></div>
            </div>
        </div>
        <div style="width: 40px;"></div>
    </div>
</template>

<script setup lang="ts">

    const props = defineProps(['info'])
    

</script>

<style scoped lang="css">
    .answer-card-outer {
        display: flex;
        background-color: #DCDCDC;
        padding: 20px 0;
    }
    .answer-card {
        border: 1px solid #F8F8FF;
        min-height: 20px;
        padding: 5px;
        border-top-left-radius: 8px;
        border-top-right-radius: 8px;
        border-bottom-right-radius: 8px;
        max-width: 500px;
        background-color: #F8F8FF;
        color: black;
        text-align: left;
        max-width: 650px
    }
</style>