import { ref } from "vue";
import ApiList from "./list";
import { getSessionId } from "./request";

export const getWebSocketApiUrl = async (arg: string) => {
    const token = ref('');
    let protocol = "ws";
    if (["ws", "wss"].includes(import.meta.env.VITE_API_WEBSOCKET_PROTOCOL)) {
        protocol = import.meta.env.VITE_API_WEBSOCKET_PROTOCOL;
    } else if (import.meta.env.VITE_API_WEBSOCKET_PROTOCOL==="auto") {
        protocol = window.location.protocol === "https:"? "wss":"ws";
    }
    await getSessionId(arg).then(res => {
        token.value = res.data.data;
    });
    const url = `${protocol}://${window.location.host}/msg?accessToken=${token.value}`;
    return url;
}