import axios from 'axios';
import { bearer } from "./bearer";

export async function refreshTokens(refreshToken) {
    const url = "http://localhost:37064/api/auth/refresh";
    const body = {
        refreshToken: refreshToken
    }
    try {
        const response = await axios.post(url, body, {
            headers: {
                'Authorization': bearer(refreshToken)
            },
        });
        if (response.status === 200) {
            return response;
        }
    } catch (error) {
        return error;
    }
}
