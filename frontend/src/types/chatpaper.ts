export interface Conversation {
    index: number;
    maxWidth: string;
}

export interface Chatparams {
    sessionId: string;
    tag: string;
    model: string;
    content: {
        question: string;
        pmid: string;
    }
}