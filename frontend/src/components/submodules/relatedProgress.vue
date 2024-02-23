<template>
    <div class="related-progress">
        <div class="p-bar">
            <div class="bar-show">
                <div class="show-p" :style="{width: lightBarWidth + 'px'}" v-if="rule !=='PP3' && rule !=='BP4'">
                    <div class="p-mark">
                        <template v-if="rule==='BS1' || rule==='BA1' || rule==='PM2'">
                            <template v-if="props.inheritance === 'AD'">
                                <div class="mark-sty mark-1">
                                    <div class="mark-tit" :style="(propValue < 0.02) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">PM2</div>
                                    <div class="mark-short" :style="(propValue < 0.02) ? {background:  color, borderColor: color} : ''"></div>
                                    <div class="mark-line" :style="(propValue < 0.02) ? {background:  color, borderColor: color} : ''"></div>
                                    <div class="mark-dot" :style="(propValue < 0.02) ? {background:  color, borderColor: color} : ''"></div>
                                </div>
                                <div class="mark-sty mark-2">
                                    <div class="mark-tit" :style="(propValue >= 0.02 && propValue < 0.1) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">BS1</div>
                                    <div class="mark-short" :style="(propValue >= 0.02 && propValue < 0.1) ? {background:  color, borderColor: color} : ''"></div>
                                    <div class="mark-line" :style="(propValue >= 0.02 && propValue < 0.1) ? {background:  color, borderColor: color} : ''"></div>
                                    <div class="mark-dot" :style="(propValue >= 0.02 && propValue < 0.1) ? {background:  color, borderColor: color} : ''"></div>
                                </div>
                                <div class="mark-sty mark-3">
                                    <div class="mark-tit" :style="(propValue >= 0.1) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">BA1</div>
                                    <div class="mark-short" :style="(propValue >= 0.1) ? {background:  color, borderColor: color} : ''"></div>
                                    <div class="mark-line" :style="(propValue >= 0.1) ? {background:  color, borderColor: color} : ''"></div>
                                    <div class="mark-dot" :style="(propValue >= 0.1) ? {background:  color, borderColor: color} : ''"></div>
                                </div>
                            </template>
                            <template v-else>
                                <div class="mark-sty mark-1">
                                    <div class="mark-tit" :style="(propValue < 0.007) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">PM2</div>
                                    <div class="mark-short" :style="(propValue < 0.07) ? {background:  color, borderColor: color} : ''"></div>
                                    <div class="mark-line" :style="(propValue < 0.07) ? {background:  color, borderColor: color} : ''"></div>
                                    <div class="mark-dot" :style="(propValue < 0.07) ? {background:  color, borderColor: color} : ''"></div>
                                </div>
                                <div class="mark-sty mark-2">
                                    <div class="mark-dou-tit tit-pm2" style="width: 44px;" :style="(propValue >= 0.007 && propValue < 0.07) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">
                                        <div style="font-size: 12px; transform: scale(0.9)" :style="(propValue >= 0.007 && propValue < 0.07) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">PM2</div>
                                        <div style="transform: scale(0.6); margin-left: -8px;white-space: nowrap;" :style="(propValue >= 0.007 && propValue < 0.07) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">Supporting</div>
                                        <div class="pm2-short" :style="(propValue >= 0.007 && propValue < 0.07) ? {background:  color, borderColor: color} : ''"></div>
                                    </div>
                                    <div class="mark-dou-tit tit-bs1" style="width: 44px;" :style="(propValue >= 0.07 && propValue < 0.3) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">
                                        <div style="font-size: 12px; transform: scale(0.9)" :style="(propValue >= 0.07 && propValue < 0.3) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">BS1</div>
                                        <div style="margin-left: -8px; transform: scale(0.6); white-space: nowrap;" :style="(propValue >= 0.07 && propValue < 0.3) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">Supporting</div>
                                        <div class="bs1-short" :style="(propValue >= 0.07 && propValue < 0.3) ? {background:  color, borderColor: color} : ''"></div>
                                    </div>
                                    <div class="mark-dou-line">
                                        <div class="dou-line1" :style="(propValue >= 0.07 && propValue < 0.3) ? {background: color, borderColor: color} : ''"></div>
                                        <div class="dou-line2" :style="(propValue >= 0.007 && propValue < 0.07) ? {background: color, borderColor: color} : ''"></div>
                                        <div class="dou-line3" :style="((propValue >= 0.07 && propValue < 0.3) || (propValue >= 0.007 && propValue < 0.07)) ? { background:  color, borderColor: color } : ''"></div>
                                    </div>
                                    <div class="mark-dot" style="margin-left: -1px;" :style="((propValue >= 0.07 && propValue < 0.3) || (propValue >= 0.007 && propValue < 0.07)) ? { background:  color, borderColor: color } : ''"></div>
                                </div>
                                <div class="mark-sty mark-6">
                                    <div class="mark-tit" :style="(propValue >= 0.3 && propValue < 0.5) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">BS1</div>
                                    <div class="mark-short" :style="(propValue >= 0.3 && propValue < 0.5) ? {background:  color, borderColor: color} : ''"></div>
                                    <div class="mark-line" :style="(propValue >= 0.3 && propValue < 0.5) ? {background:  color, borderColor: color} : ''"></div>
                                    <div class="mark-dot" :style="(propValue >= 0.3 && propValue < 0.5) ? {background:  color, borderColor: color} : ''"></div>
                                </div>
                                <div class="mark-sty mark-7">
                                    <div class="mark-tit" :style="(propValue >= 0.5) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">BA1</div>
                                    <div class="mark-short" :style="(propValue >= 0.5) ? {background:  color, borderColor: color} : ''"></div>
                                    <div class="mark-line" :style="(propValue >= 0.5) ? {background:  color, borderColor: color} : ''"></div>
                                    <div class="mark-dot" :style="(propValue >= 0.5) ? {background:  color, borderColor: color} : ''"></div>
                                </div>
                            </template>
                        </template>
                        <template v-if="rule==='BS2'">
                            <template v-if="props.inheritance === 'AD'">
                                <div class="mark-sty mark-1">
                                    <div class="mark-tit" :style="propValue > 1 ? {background: color, borderColor: color, color: '#ffffff'} : ''">BS2</div>
                                    <div class="mark-short" :style="propValue > 1 ? {background: color, borderColor: color} : ''"></div>
                                    <div class="mark-line" :style="propValue > 1 ? {background: color, borderColor: color} : ''"></div>
                                    <div class="mark-dot" :style="propValue > 1 ? {background: color, borderColor: color} : ''"></div>
                                </div>
                            </template>
                            <template v-else>
                                <div class="mark-sty mark-4">
                                    <div class="mark-tit" :style="propValue > 3 ? {background: color, borderColor: color, color: '#ffffff'} : ''">BS2</div>
                                    <div class="mark-short" :style="propValue > 3 ? {background: color, borderColor: color} : ''"></div>
                                    <div class="mark-line" :style="propValue > 3 ? {background: color, borderColor: color} : ''"></div>
                                    <div class="mark-dot" :style="propValue > 3 ? {background: color, borderColor: color} : ''"></div>
                                </div>
                            </template>
                        </template>
                    </div>
                    <div class="p-scale">
                        <template v-if="rule==='BS1' || rule==='BA1' || rule==='PM2'">
                            <template v-if="props.inheritance === 'AD'">
                                <span class="scale-origin">0%</span>
                                <span class="scale-mark1">0.002%</span>
                                <span class="scale-mark2">0.02%</span>
                                <span class="scale-mark3">0.1%</span>
                                <span class="scale-end">100%</span>
                            </template>
                            <template v-else>
                                <span class="scale-origin">0%</span>
                                <span class="scale-mark1">0.007%</span>
                                <span class="scale-mark8">0.07%</span>
                                <span class="scale-mark6">0.3%</span>
                                <span class="scale-mark7">0.5%</span>
                                <span class="scale-end">100%</span>
                            </template>
                        </template>
                        <template v-if="rule==='BS2'">
                            <template v-if="props.inheritance === 'AD'">
                                <span class="scale-origin" style="left: 0;">0</span>
                                <span class="scale-mark4">1</span>
                                <span class="scale-end" style="right: 0;">100000</span>
                            </template>
                            <template v-else>
                                <span class="scale-origin" style="left: 0;">0</span>
                                <span class="scale-mark5">3</span>
                                <span class="scale-end" style="right: 0;">100000</span>
                            </template>
                        </template>
                    </div>
                </div>
                <div class="show-p" style="background: linear-gradient(90deg, #43CF7C 0%, #D43030 100%);" :style="{width: lightBarWidth + 'px'}" v-if="rule === 'PP3' || rule ==='BP4'">
                    <div class="p-mark">
                        <div class="mark-sty mark-bp4">
                            <div class="mark-tit" :style="(propValue < 0.15) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">BP4</div>
                            <div class="mark-short" :style="(propValue < 0.15) ? {background:  color, borderColor: color} : ''"></div>
                            <div class="mark-line" :style="(propValue < 0.15) ? {background:  color, borderColor: color} : ''"></div>
                            <div class="mark-dot" :style="(propValue < 0.15) ? {background:  color, borderColor: color} : ''"></div>
                        </div>
                        <div class="mark-sty mark-pp3">
                            <div class="mark-tit" :style="(propValue >= 0.7) ? {background:  color, borderColor: color, color: '#ffffff'} : ''">PP3</div>
                            <div class="mark-short" :style="(propValue >= 0.7) ? {background:  color, borderColor: color} : ''"></div>
                            <div class="mark-line" :style="(propValue >= 0.7) ? {background:  color, borderColor: color} : ''"></div>
                            <div class="mark-dot" :style="(propValue >= 0.7) ? {background:  color, borderColor: color} : ''"></div>
                        </div>
                    </div>
                    <div class="p-scale">
                        <span class="scale-origin" style="left: 0;">0</span>
                        <span class="scale-mark9">0.15</span>
                        <span class="scale-mark10">0.7</span>
                        <span class="scale-end" style="right: 0;">1</span>
                    </div>
                    <div class="show-tt" :style="{left: lightBarWidth <= 350 ? lightBarWidth + 'px' : '350px'}">{{ propValue / 100 }}</div>
                </div>
                <div v-if="rule==='BS1' || rule==='BA1' || rule==='PM2'" class="show-t" :style="{width: propTextWidth + 'px'}">{{ propValue }}%</div>
                <div v-else-if="rule === 'BS2'" class="show-t" :style="{width: propTextWidth + 'px'}">{{ propValue / 100 }}</div>
            </div>
        </div>
    </div>
</template>

<script setup lang='ts'>
    import { ref, onMounted, watch } from 'vue';
    import { useSummary } from "@/store";

    const props = defineProps(['info', 'inheritance']);
    const { rule, strength } = props.info.rowData;
    const propValue = ref<number>(props.info.rowData.af ? Number((Number(props.info.rowData.af)*100).toFixed(4)) : 0);
    const color = ref<string>('');

    const lightBarWidth = ref(0);
    const propTextWidth = ref(400);

    const caculatorStyle = () => {
        let val = (rule==='BS1' || rule==='BA1' || rule==='PM2') ? propValue.value : propValue.value / 100;
        if(rule==='BS1' || rule==='BA1' || rule==='PM2') {
            if(props.inheritance === 'AD') {
                if(val === 0.002) { lightBarWidth.value = 40; propTextWidth.value = 360; }
                if(val > 0.002 && val <= 0.0065) { lightBarWidth.value = 60; propTextWidth.value = 340; }
                if(val > 0.0065 && val <= 0.011) { lightBarWidth.value = 80; propTextWidth.value = 320; }
                if(val > 0.011 && val <= 0.0155) { lightBarWidth.value = 100; propTextWidth.value = 300; }
                if(val > 0.0155 && val < 0.02) { lightBarWidth.value = 120; propTextWidth.value = 280; }
                if(val === 0.02) { lightBarWidth.value = 140; propTextWidth.value = 260; }
                if(val > 0.02 && val <= 0.03333) { lightBarWidth.value = 160; propTextWidth.value = 240; }
                if(val > 0.03333 && val <= 0.04666) { lightBarWidth.value = 180; propTextWidth.value = 220; }
                if(val > 0.04666 && val <= 0.06) { lightBarWidth.value = 200; propTextWidth.value = 200; }
                if(val > 0.06 && val <= 0.07333) { lightBarWidth.value = 220; propTextWidth.value = 180; }
                if(val > 0.07333 && val <= 0.08666) { lightBarWidth.value = 240; propTextWidth.value = 160; }
                if(val > 0.08666 && val < 0.1) { lightBarWidth.value = 260; propTextWidth.value = 140; }
                if(val === 0.1) { lightBarWidth.value = 280; propTextWidth.value = 120; }
                if(val > 0.1) { lightBarWidth.value = 320; propTextWidth.value = 80; }
                if(val < 0.002) { color.value = matchColor(strength, 'PM2') }
                if(val >= 0.02 && val < 0.1) { color.value = matchColor(strength, 'BS1') }
                if(val >= 0.1) { color.value = matchColor(strength, 'BA1') }
            }else{
                if(val > 0 && val < 0.007) { lightBarWidth.value = 20; propTextWidth.value = 380; }
                if(val === 0.007) { lightBarWidth.value = 40; propTextWidth.value = 360; }
                if(val > 0.007 && val < 0.0385) { lightBarWidth.value = 80; propTextWidth.value = 320; }
                if(val >= 0.0385 && val < 0.07) { lightBarWidth.value = 120; propTextWidth.value = 280; }
                if(val === 0.07) { lightBarWidth.value = 140; propTextWidth.value = 260; }
                if(val > 0.07 && val < 0.185) { lightBarWidth.value = 160; propTextWidth.value = 240; }
                if(val >= 0.185 && val < 0.3) { lightBarWidth.value = 200; propTextWidth.value = 200; }
                if(val === 0.3) { lightBarWidth.value = 220; propTextWidth.value = 180; }
                if(val > 0.3 && val < 0.4) { lightBarWidth.value = 240; propTextWidth.value = 160; }
                if(val === 0.4) { lightBarWidth.value = 260; propTextWidth.value = 140; }
                if(val > 0.4 && val < 0.5) { lightBarWidth.value = 280; propTextWidth.value = 120; }
                if(val === 0.5) { lightBarWidth.value = 300; propTextWidth.value = 100; }
                if(val > 0.5) { lightBarWidth.value = 320; propTextWidth.value = 80; }
                if(val < 0.007) { color.value = matchColor(strength, 'PM2') }
                if(val >= 0.007 && val < 0.07) { color.value = matchColor(strength, 'PM2') }
                if(val >= 0.07 && val < 0.3) { color.value = matchColor(strength, 'BS1') }
                if(val >= 0.3 && val < 0.5) { color.value = matchColor(strength, 'BS1') }
                if(val >= 0.5) { color.value = matchColor(strength, 'BA1') }
            }
        }
        if(rule === 'BS2') {
            if(props.inheritance === 'AD') {
                if(val === 0) { lightBarWidth.value = 0; propTextWidth.value = 400; }
                if(val > 0 && val < 1) { lightBarWidth.value = 20; propTextWidth.value = 380; }
                if(val === 1 || val === 1.0 ) { lightBarWidth.value = 40; propTextWidth.value = 360; }
                if(val > 1) { lightBarWidth.value = 80; propTextWidth.value = 320; color.value = matchColor(strength, rule) }
            }else{
                if(val === 0) { lightBarWidth.value = 0; propTextWidth.value = 400; }
                if(val > 0 && val <= 1) { lightBarWidth.value = 40; propTextWidth.value = 360; }
                if(val > 1 && val <= 2 ) { lightBarWidth.value = 60; propTextWidth.value = 340; }
                if(val > 2 && val < 3) { lightBarWidth.value = 80; propTextWidth.value = 320; }
                if(val === 3 || val === 3.0) { lightBarWidth.value = 100; propTextWidth.value = 300; }
                if(val > 3) { lightBarWidth.value = 140; propTextWidth.value = 260; color.value = matchColor(strength, rule) }
            }
        }
        if(rule === 'PP3' || rule === 'BP4') {
            if(val === 0) { lightBarWidth.value = 0; }
            if(val > 0) {
                lightBarWidth.value = Math.floor(400 * val);
            }
            if(val <= 0.15) { color.value = matchColor(strength, 'BP4') }
            if(val > 0.7) { color.value = matchColor(strength, 'PP3') }
        }
    }

    const summary = useSummary();
    const matchColor = (strength: string, name: string) => {
        switch (strength) {
            case 'P':
                return summary.criteriaPalette[name.split("_")[0].slice(0, 1).toLocaleLowerCase() + "p"]
            case 'M':
                return summary.criteriaPalette[name.split("_")[0].slice(0, 1).toLocaleLowerCase() + "m"]
            case 'S':
                return summary.criteriaPalette[name.split("_")[0].slice(0, 1).toLocaleLowerCase() + "s"]
            case 'Supporting':
                return summary.criteriaPalette[name.split("_")[0].slice(0, 1).toLocaleLowerCase() + "p"]
            case 'Moderate':
                return summary.criteriaPalette[name.split("_")[0].slice(0, 1).toLocaleLowerCase() + "m"]
            case 'Strong':
                return summary.criteriaPalette[name.split("_")[0].slice(0, 1).toLocaleLowerCase() + "s"]
            case 'Unmet':
            case null:
            case '':
                return summary.criteriaPalette['unmet'];
        }
    };

    watch(()=> props.inheritance, ()=> {
        caculatorStyle();
    })
    
    onMounted(()=> {
        caculatorStyle();
    })
    
</script>

<style lang='scss' scoped>
    .related-progress {
        width: 100%;
        .p-bar {
            // position: relative;
            width: 400px;
            height: 15px;
            border: 1px solid #D0D5DB;
            border-radius: 10px;
            margin-top: 60px;
            margin-bottom: 20px;
            .bar-show {
                width: 100%;
                height: 100%;
                display: flex;
                .show-p {
                    // width: 320px;
                    height: 100%;
                    border-radius: 10px;
                    background-color: #2A82E4;
                    position: relative;
                    .p-mark {
                        position: absolute;
                        left: 0;
                        bottom: 0;
                        width: 320px;
                        .mark-sty {
                            position: absolute;
                            bottom: 0;
                            .mark-tit {
                                width: 35px;
                                padding: 3px 0;
                                border: 1px solid #808080;
                                background-color: #E5E5E5;
                                border-radius: 4px;
                                font-size: 12px;
                                text-align: center;
                                color: #000000;
                            }
                            .mark-dou-tit {
                                padding: 0 0;
                                border: 1px solid #808080;
                                background-color: #E5E5E5;
                                text-align: left;
                                border-radius: 4px;
                                font-size: 12px;
                                color: #000000;
                                line-height: 1;
                            }
                            .tit-pm2 {
                                position: absolute;
                                bottom: 47px;
                                left: -50px;
                                .pm2-short {
                                    width: 6px;
                                    height: 2px;
                                    background-color: #808080;
                                    position: absolute;
                                    bottom: -3px;
                                    right: 7px;
                                }
                            }
                            .tit-bs1 {
                                position: absolute;
                                bottom: 47px;
                                left: 0px;
                                .bs1-short {
                                    width: 6px;
                                    height: 2px;
                                    background-color: #808080;
                                    position: absolute;
                                    bottom: -3px;
                                    left: 10px;
                                }
                            }
                            .mark-short {
                                width: 6px;
                                height: 2px;
                                background-color: #808080;
                                margin-left: 16.5px;
                            }
                            .mark-line {
                                width: 1px;
                                height: 40px;
                                background-color: #808080;
                                margin-left: 19px;
                            }
                            .mark-dou-line {
                                // margin-left: 19px;
                                position: relative;
                                .dou-line1 {
                                    width: 1px;
                                    height: 20px;
                                    background-color: #808080;
                                    transform: rotate(45deg);
                                    position: absolute;
                                    left: 7px;
                                    top: -17px;
                                }
                                .dou-line2 {
                                    width: 1px;
                                    height: 20px;
                                    background-color: #808080;
                                    transform: rotate(-45deg);
                                    position: absolute;
                                    left: -7px;
                                    top: -17px;
                                }
                                .dou-line3 {
                                    width: 1px;
                                    height: 27px;
                                    background-color: #808080;
                                }
                            }
                            .mark-dot {
                                width: 4px;
                                height: 4px;
                                border-radius: 50%;
                                background-color: #808080;
                                margin-left: 18px;
                            }
                        }
                        .mark-1 {
                            left: 20px;
                        }
                        .mark-2 {
                            left: 120px;
                        }
                        .mark-3 {
                            left: 260px;
                        }
                        .mark-4 {
                            left: 80px;
                        }
                        .mark-6 {
                            left: 200px;
                        }
                        .mark-7 {
                            left: 280px;
                        }
                        .mark-bp4 {
                            left: 40px;
                        }
                        .mark-pp3 {
                            left: 260px;
                        }
                    }
                    .p-scale {
                        position: absolute;
                        left: 0;
                        top: 17px;
                        width: 400px;
                        color: #808080;
                        span {
                            position: absolute;
                        }
                        .scale-origin {
                            left: -10px;
                        }
                        .scale-mark1 {
                            left: 20px;
                        }
                        .scale-mark2 {
                            left: 125px;
                        }
                        .scale-mark3 {
                            left: 265px;
                        }
                        .scale-mark4 {
                            left: 36px;
                        }
                        .scale-mark5 {
                            left: 96px;
                        }
                        .scale-mark6 {
                            left: 208px;
                        }
                        .scale-mark7 {
                            left: 288px;
                        }
                        .scale-mark8 {
                            left: 105px
                        }
                        .scale-mark9 {
                            left: 50px;
                        }
                        .scale-mark10 {
                            left: 272px;
                        }
                        .scale-end {
                            right: -20px;
                        }
                    }
                    .show-tt {
                        position: absolute;
                        width: 60px;
                        bottom: 0;
                        height: 100%;
                        color: #000000;
                        text-align: left;
                        text-indent: 6px;
                        font-size: 14px;
                        line-height: 16px;
                    }
                }
                .show-t {
                    height: 100%;
                    color: #2A82E4;
                    text-align: left;
                    text-indent: 6px;
                    font-size: 14px;
                    line-height: 16px;
                }
            }   
        }
    }
</style>