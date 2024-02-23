import axios from 'axios';
import { useGlobalInfo } from '@/store';

const globalInfo = useGlobalInfo();

const requests = (url: string, method: string, arg: any) => {
    const request = axios.create({ 
        baseURL: url,
        method: method,
        data: arg,
        timeout: 60000
    })
    request.interceptors.response.use((config)=> {
        config.headers.loginToken = globalInfo.tokenStatus;
        return config
    })
}

const axiosRequest = {
    post: (url: string, arg: any) => requests(url, 'post', arg),
    get: (url: string, arg: any) => requests(url, 'get', arg)
}

export default axiosRequest;





