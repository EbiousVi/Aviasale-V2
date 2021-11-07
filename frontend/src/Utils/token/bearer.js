export function bearer(token) {
    let bearer = "Bearer ".concat(token);
    return bearer;
}