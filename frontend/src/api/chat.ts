import { ref } from "vue";
import { getSessionId } from "./request";

export interface IWebSocketApiUrl {
    url: string;
    sessionId: string;
}

export const getWebSocketApiUrl = async (arg: string) => {
    const token = ref('');
    let protocol = "ws";
    if (["ws", "wss"].includes(import.meta.env.VITE_API_WEBSOCKET_PROTOCOL)) {
        protocol = import.meta.env.VITE_API_WEBSOCKET_PROTOCOL;
    } else if (import.meta.env.VITE_API_WEBSOCKET_PROTOCOL==="auto") {
        protocol = window.location.protocol === "https:" ? "wss":"ws";
    }
    await getSessionId(arg).then(res => {
        token.value = res.data.data;
    });
    const url = `${protocol}://${window.location.host}/test/msg?accessToken=${token.value}`;
    return <IWebSocketApiUrl>{
        url: url,
        sessionId: token.value
    };
}