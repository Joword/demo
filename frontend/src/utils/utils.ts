// 工具集
import { CSSProperties } from "vue";
import { createDiscreteApi } from "naive-ui";


export const geneStyle = (info:{ focused:boolean, checked:boolean}) => {
    const style: CSSProperties = {};
    if (info.checked) {
      style.background = '#ecf0f1';
      if (info.focused) {
        style.boxShadow = '0 0 0 2px #ecf0f140';
      }
    } else {
      style.background = '#7948EA';
      if (info.focused) {
        style.boxShadow = '0 0 0 2px #7948EA40';
      }
    }
    return style;
}
export const variantStyle = (info:{ focused:boolean, checked:boolean}) => {
    const style: CSSProperties = {};
    if (info.checked) {
      style.background = '#ecf0f1';
      if (info.focused) {
        style.boxShadow = '0 0 0 2px #ecf0f140';
      }
    } else {
      style.background = '#00BAAD';
      if (info.focused) {
        style.boxShadow = '0 0 0 2px #00BAAD40';
      }
    }
    return style;
}
export const diseaseStyle = (info:{ focused:boolean, checked:boolean}) => {
    const style: CSSProperties = {};
    if (info.checked) {
      style.background = '#ecf0f1';
      if (info.focused) {
        style.boxShadow = '0 0 0 2px #ecf0f140';
      }
    } else {
      style.background = '#FF8D1A';
      if (info.focused) {
        style.boxShadow = '0 0 0 2px #FF8D1A40';
      }
    }
    return style;
}

export const compareSort = (prop: any) => {
    return (obj1: any, obj2: any)=> {
        let val1 = obj1[prop];
        let val2 = obj2[prop];
        if (!isNaN(Number(val1)) && !isNaN(Number(val2))) {
            val1 = Number(val1);
            val2 = Number(val2);
        }
        if (val1 < val2) {
            return -1;
        } else if (val1 > val2) {
            return 1;
        } else {
            return 0;
        }            
    }
} 

export function on(
    element: Element | HTMLElement | Document | Window,
    event: string,
    handler: EventListenerOrEventListenerObject,
  ): void {
    if (element && event && handler) {
      element.addEventListener(event, handler, false);
    }
}

export const isServer = typeof window === 'undefined';

const { message, dialog } = createDiscreteApi(
  ["message", "dialog"],
);

export {
  message as Message,
  dialog as Dialog,
};
