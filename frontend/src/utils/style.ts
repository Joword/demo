import { CSSProperties } from 'vue';
export const SwitchStyle = ({ focused, checked }:{ focused: boolean; checked: boolean; }) => {
    const style: CSSProperties = {};
    if (checked) {
        style.background = '#2A82E4';
        if (focused) {
            style.boxShadow = '0 0 0 2px #57c3c240';
        }
    } else {
        style.background = "#cccccc";
        if (focused) {
            style.boxShadow = '0 0 0 2px #f5f5f540';
        }
    }
    return style;
}