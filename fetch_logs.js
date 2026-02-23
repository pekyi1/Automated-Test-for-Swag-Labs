const https = require('https');
const fs = require('fs');
const zlib = require('zlib');

const repo = 'pekyi1/Automated-Test-for-XYZ-Bank';
const jobId = '64498507099'; // From previous script output

const options = {
    hostname: 'api.github.com',
    path: `/repos/${repo}/actions/jobs/${jobId}/logs`,
    method: 'GET',
    headers: {
        'User-Agent': 'Node.js',
        'Accept': 'application/vnd.github.v3+json'
    }
};

https.get(options, (res) => {
    if (res.statusCode === 302 || res.statusCode === 301) {
        // Handle redirect to download URL
        const redirectUrl = res.headers.location;
        console.log(`Redirecting to download logs...`);

        https.get(redirectUrl, (logRes) => {
            const dest = fs.createWriteStream('./job_logs.txt');
            logRes.pipe(dest);
            dest.on('finish', () => {
                console.log('Logs downloaded to job_logs.txt');
            });
        }).on('error', (e) => {
            console.error(`Error downloading logs: ${e.message}`);
        });
    } else {
        console.error(`Failed to fetch logs. Status Code: ${res.statusCode}`);
    }
}).on('error', (e) => {
    console.error(`Got error: ${e.message}`);
});
