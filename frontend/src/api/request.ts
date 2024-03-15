import axios from "axios";
import ApiList from "@/api/list";
import { RegisterInfo, emailCodeForm, loginForm, changePassword, chatForm } from "@/types/info";

export const registerApi = (register: RegisterInfo) => {
    return axios.post(ApiList.Register, register);
}

export const testEmailApi = (email: FormData) => {
    return axios.post(ApiList.IsEmail, email);
}

export const getEmailCode = (email: emailCodeForm) => {
    return axios.post(ApiList.sendCode, email)
}

export const testLogin = (login: loginForm) => {
    return axios.post(ApiList.login, login);
}

export const testForgot = (email: emailCodeForm) => {
    return axios.post(ApiList.forgotPwd, email);
}

export const changePwdInfo = () => {
    return axios.post(ApiList.changepassword);
}

export const changePwd = (arg: changePassword) => {
    return axios.post(ApiList.changePwd, arg);
}

export const checkLogin = () => {
    return axios.post(ApiList.check)
}

export const logout = () => {
    return axios.post(ApiList.logout);
}

export const getRelevance = (arg: string) => {
    return axios.post(ApiList.relevance, arg);
}

export const getVariantId = (arg: FormData) => {
    return axios.post(ApiList.variantId, arg);
}

export const getGene = (arg: FormData) => {
    return axios.post(ApiList.gene, arg);
}

export const getLikeVariantId = (arg: FormData) => {
    return axios.post(ApiList.likeVariantId, arg);
}

export const getLikeGene = (arg: FormData) => {
    return axios.post(ApiList.likeGene, arg);
}

export const addPmidInfo = (arg: FormData) => {
    return axios.post(ApiList.pmidInfo, arg);
}

export const getClassficationTable = (arg: FormData) => {
    return axios.post(ApiList.clzTable, arg);
}

export const updateEvidence = (arg: any) => {
    return axios.post(ApiList.updateEvidence, arg);
}

export const deleteEvidence = (arg: any) => {
    return axios.post(ApiList.deleteEvidence, arg);
}

export const getPmidAndGeneData = (pmid: any) => {
    return axios.post(ApiList.pmidAndGeneData + `?p=${pmid}`);
}

export const submitCritrtiaStatus = (variantId: any) => {
    return axios.post(ApiList.submitStatus, variantId);
}

export const getChatPaperData = (arg: chatForm) => {
    return axios.post(ApiList.chatPaperData, arg);
}

export const getSessionId = (arg: string) => {
    return axios.post(ApiList.getSessionId, arg);
}

export const getPublicationList = (vd: string | any) => {
    return axios.get(ApiList.publicationList + `?vd=${vd}`);
}

export const getPublicationSearch = (id: string) => {
    return axios.get(ApiList.publicationSearch + `?id=${id}`);
}

export const getPublicationUpdate = (arg: any) => {
    return axios.post(ApiList.publicationUpdate, arg)
}

export const getClinicalEvidence = (vd: string) => {
    return axios.get(ApiList.clinicalEvidence + `?vd=${vd}`)
}