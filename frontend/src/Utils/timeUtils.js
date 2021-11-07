export function parseTime(date, timeZone) {
    return new Date(date).toLocaleTimeString(["ru-RU"], {
        hour: "2-digit",
        minute: "2-digit",
        timeZone: timeZone,
        timeZoneName: 'short'
    });
}

export function parseDate(val) {
    return new Date(val).toLocaleDateString("en-EN", {
        year: "numeric",
        month: "short",
        day: "numeric",

    });
}

export function duration(d1, d2) {
    const date1 = Date.parse(d1);
    const date2 = Date.parse(d2);
    const diff = date2 - date1;
    const d = Math.floor(diff / (1000 * 60 * 60 * 24));
    const m = Math.floor((diff / (1000 * 60)) % 60);
    const h = Math.floor((diff / (1000 * 60 * 60)) % 24);
    if (d > 0) {
        return d + "D " + h + "H " + m + "M";
    } else {
        return h + "H " + m + "M";
    }
}
