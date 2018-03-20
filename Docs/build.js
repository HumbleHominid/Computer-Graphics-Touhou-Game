const fs = require('fs');

// If there is a previos doc, delete it
if (fs.existsSync('srs.md')) {
    fs.unlinkSync('srs.md');
}

fs.writeFileSync('srs.md', '');

// Hard coded search through `Use_Cases` dir for now
let caseData = JSON.parse(fs.readFileSync('Use_Cases/.build'));
caseData.forEach((useCase) => {
    let fileData = fs.readFileSync(`Use_Cases/${useCase.file}`);

    fs.appendFileSync('srs.md', `### ${useCase.name}\n${fileData}\n`);
});
