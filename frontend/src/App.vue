<template>
    <n-config-provider :theme-overrides="themeOverrides">
        <div id="router">
            <router-view/>
        </div>
    </n-config-provider>

</template>

<script setup lang="ts">
    import { GlobalThemeOverrides  } from "naive-ui";

    const themeOverrides: GlobalThemeOverrides = {
        common: {
            primaryColor: '#235BA3',
            primaryColorHover: '#235BA3',
        },
  }
</script>

<style>
    #router {
      font-family: "-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, 'Noto Sans', sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji'";
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
    }
</style>