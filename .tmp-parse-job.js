const fs = require('fs');
const html = fs.readFileSync('.tmp-job-page.html', 'utf8');
const re = /<script[^>]*type="application\/json"[^>]*>(.*?)<\/script>/gs;
const matches = [...html.matchAll(re)];
console.log('scripts', matches.length);
for (let i = 0; i < matches.length; i++) {
  const txt = matches[i][1];
  if (txt.includes('Run API tests') || /failure|output|step|logs/.test(txt.toLowerCase())) {
    console.log('---SCRIPT', i + 1, '---');
    console.log(txt.slice(0, 12000));
    console.log('---END---');
  }
}
