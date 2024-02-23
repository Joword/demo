<template>
    <div class="criteria-dialog-title">
        <span class="rule">{{props.rule}}</span><span class="strength">{{ props.strength }}</span>
    </div>
</template>

<script setup lang='ts'>
    const props = defineProps({
        rule: String,
        strength: String
    })
</script>

<style lang='scss' scoped>
    .criteria-dialog-title {
        width: 100%;
        text-align: center;
        font-weight: bold;
        .rule {
            font-size: 20px;
            margin-right: 10px;
        }
        .strength {
            font-size: 14px;
        }
    }
</style>