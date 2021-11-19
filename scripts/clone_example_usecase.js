// imports
const {promisify} = require('util');
const {resolve} = require('path');
const fs = require('fs');
const readdir = promisify(fs.readdir);
const stat = promisify(fs.stat);

// constantes
const baseuseCase = "ExampleUseCase"
const basePackage = "com/xbidi/spring";

// nombres para reemplazar
const useCase = process.argv.slice(2)[0];
const useCaseLowerCase = useCase.toLowerCase();
const useCaseCamelCase = toCamelCase(useCase);
const useCaseCamelCaseFirsUpperCase = toUpperFistLetter(useCaseCamelCase);
const useCaseUpperSnakeCase = toUpperSnakeCase(useCase);

// directorios para trabajar
const currentDir = __dirname;
const filesDir = `${currentDir}/src/main/java/${basePackage}/content/usecase`;
const filesDestinationDir = `${currentDir}/src/main/java/${basePackage}/content/usecase/`;
const testsDir = `${currentDir}/src/test/java/${basePackage}/content/usecase`;
const testsDestinationDir = `${currentDir}/src/test/java/${basePackage}/content/usecase/`;

// copia los ficheros originales al nuevo directorio
copyFiles(filesDir, filesDestinationDir).then(() => {/**/
});

copyFiles(testsDir, testsDestinationDir).then(() => {/**/
});

function replaceUseCaseNames(string) {
    let response = string;
    response = response.replaceAll(baseuseCase.toLowerCase(), useCaseLowerCase)
    response = response.replaceAll(baseuseCase, useCaseCamelCaseFirsUpperCase)
    return response.replaceAll(toUpperSnakeCase(baseuseCase), useCaseUpperSnakeCase)
}

async function copyFiles(originalDir, destinationDir) {
    getFiles(originalDir).then(files => {
        for (let file of files) {
            const destinationFile = replaceUseCaseNames(file.replace(originalDir, destinationDir));
            const destinationFileDir = destinationFile.substring(0, destinationFile.lastIndexOf("/") + 1);
            fs.mkdirSync(destinationFileDir, {recursive: true});
            const content = replaceUseCaseNames(fs.readFileSync(file, 'utf8'));
            if (fs.existsSync(destinationFile)) fs.unlinkSync(destinationFile)
            fs.writeFileSync(destinationFile, content);
        }
    });
}

async function getFiles(dir) {
    const subDirs = await readdir(dir);
    const files = await Promise.all(subDirs.map(async (subDir) => {
        const res = resolve(dir, subDir);
        return (await stat(res)).isDirectory() ? getFiles(res) : res;
    }));
    return files.reduce((a, f) => a.concat(f), []);
}


function toCamelCase(str) {
    return str.replace(/^\w|[A-Z]|\b\w/g, function (word, index) {
        return index === 0 ? word.toLowerCase() : word.toUpperCase();
    }).replace(/\s+/g, '');
}

function toUpperSnakeCase(string) {
    return string.replace(/[A-Z]/g, letter => `_${letter.toLowerCase()}`).toUpperCase();
}

function toUpperFistLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}
