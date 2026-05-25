const fs = require('fs');
const html = fs.readFileSync('.tmp-job-page.html', 'utf8');
const re = /<script[^>]*type="application\/json"[^>]*>(.*?)<\/script>/gs;
const matches = [...html.matchAll(re)];
for (let i = 0; i < matches.length; i++) {
  const txt = matches[i][1];
  const lower = txt.toLowerCase();
  console.log('SCRIPT', i + 1, 'len', txt.length, 'containsRun', txt.includes('Run API tests'), 'containsApiTests', txt.includes('api-tests'), 'containsError', lower.includes('error'), 'containsLogs', lower.includes('logs'));
  if (txt.includes('Run API tests') || txt.includes('api-tests') || lower.includes('logs') || lower.includes('error')) {
    console.log(txt.slice(0, 2000));
  }
}
