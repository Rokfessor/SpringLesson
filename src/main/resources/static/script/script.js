let loadedImage;
init();

function init() {
    let imageLoader = document.getElementById('imageLoader');
    loadedImage = document.getElementById('loadedImage');

    document.getElementById('submitButton').addEventListener('click', () => {
        let postSended = sendPost(loadedImage.src);
        if (postSended)
            document.getElementById('spinner').style.display = 'flex';
    });

    imageLoader.addEventListener('change', event => {
        const files = event.target.files;
        const file = files[0];
        loadAndShowImage(file);
    });

    let widthRange = document.getElementById('widthRange');
    widthRange.addEventListener('input', () => {
        document.getElementById('widthTextVal').innerText = 'Ширина изображения:' + widthRange.value;
    });

    let heightRange = document.getElementById('heightRange');
    heightRange.addEventListener('input', () => {
        document.getElementById('heightTextVal').innerText = 'Высота изображения:' + heightRange.value;
    });
}

function loadAndShowImage(file) {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.addEventListener('load', (event) => {
        loadedImage.src = event.target.result;
        loadedImage.style.display = 'block';
        loadedImage.addEventListener('load', () => {
            document.getElementById('submitButton').disabled = false;
            let wRange = document.getElementById('widthRange');
            let hRange = document.getElementById('heightRange');
            wRange.max = Math.ceil(loadedImage.naturalWidth);
            wRange.min = Math.ceil(loadedImage.naturalWidth * 0.1);
            wRange.value = Math.ceil(loadedImage.naturalWidth / 2);
            wRange.dispatchEvent(new Event('input'));
            hRange.max = Math.ceil(loadedImage.naturalHeight);
            hRange.min = Math.ceil(loadedImage.naturalHeight * 0.1);
            hRange.value = Math.ceil(loadedImage.naturalHeight / 2);
            hRange.dispatchEvent(new Event('input'));
        });
    });
}

function sendPost(image) {
    let requestBody = {
        image: image,
        width: document.getElementById('widthRange').value,
        height: document.getElementById('heightRange').value,
        saveScale: document.getElementById('saveImageScale').checked
    };
    let effectType = document.getElementById('effectSelector').value;

    if (effectType === '') {
        alert('Выбирете эффект!');
        return false;
    }

    let params = {
        effect: effectType
    }

    let url = 'http://localhost:8080/api/process_image?effect=' + effectType;
    //url.search = new URLSearchParams(params).toString();

    fetch(url, {
            method: 'post',
            body: JSON.stringify(requestBody),
            headers: {
                'Content-type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok)
                return response.json();
            else
                alert('Произошла ошибка!');
        })
        .then(json => {
            document.getElementById('spinner').style.display = 'none';
            if (json != undefined) {
                document.getElementById('processedImageContainer').style.display = 'block';
                document.getElementById('processedImage').src = json.image;
            }
        })
        .catch(error => console.log(error));

    return true;
}