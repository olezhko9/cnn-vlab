const getMatrixElement = function (size, editable = false, matrix = null) {
    if (!matrix) matrix = getEmptyMatrix(size);

    let matrixStr = ""
    for (let i = 0; i < size; i++) {
        matrixStr += '<tr>'
        for (let j = 0; j < size; j++) {
            matrixStr += '<td class="matrix_cell">'
            if (editable) {
                matrixStr += `<input/>`
            } else {
                matrixStr += `<span>${matrix[i][j] == null ? "" : matrix[i][j]}</span>`
            }
            matrixStr += '</td>'
        }
        matrixStr += '</tr>'
    }

    return '<table class="matrix">' + matrixStr + '</table>'
}

const getEmptyMatrix = function (size) {
    return Array(size).fill().map(
        ()=>Array(size).fill()
    )
}

var Vlab = {

    byDefault: {
        input: {
            data: [[1,2,3],[4,5,6],[7,8,9]]
        },
        conv: {
            kernel: [[0,1],[-1,0]],
            stride: 1,
            padding: 1
        },
        pool: {
            size: 2,
            type: 0
        }
    },
    answer: {
        convResult: getEmptyMatrix(4),
        poolResult: getEmptyMatrix(2)
    },

    setVariant : function(str){
        console.log('setVariant.variant', str);
        if (str) {
            try {
                return JSON.parse(str);
            } catch (err) {
                return this.byDefault;
            }
        } else {
            return this.byDefault;
        }
    },
    setPreviousSolution: function(str){
        let prevSolution;
        if (str) {
            try {
                prevSolution = JSON.parse(str);
            } catch (err) {}
        }
        return prevSolution;
    },
    setMode: function(str){},

    //Инициализация ВЛ
    init : function(){
        let variant = this.setVariant($("#preGeneratedCode").val());
        let previousSolution = this.setPreviousSolution($("#previousSolution").val());
        console.log('init.variant', variant);
        console.log('init.previousSolution', previousSolution);
        if (previousSolution !== undefined) {
            answer = previousSolution;
        }

        function onMatrixUpdate(i, j) {
            console.log(i, j);
        }

        const content = `
            <table class="table table-bordered">
                <tr>
                    <td class="d-flex justify-content-between">
                        <h2>CNN</h2>
                        <button type="button" class="btn btn-info" id="infoModalOpener">Справка</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table class="table table-bordered" id="table">
                            <thead>
                                <td>Слой</td>
                                <td>Входные данные слоя</td>
                                <td>Выходные данные слоя</td>
                            </thead>
                            <tr>
                                <td>
                                    <p><b>Сверточный слой</b></p>
                                    <p>padding = ${variant.conv.padding}</p>
                                    <p>stride = ${variant.conv.stride}</p>
                                    <p>kernel</p>
                                    <p>${getMatrixElement(variant.conv.kernel.length, false, variant.conv.kernel)}</p>
                                </td>
                                <td>
                                    ${getMatrixElement(variant.input.data.length, false, variant.input.data)}
                                </td>
                                <td>
                                    ${getMatrixElement(4, true, null)}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <p><b>Слой пулинга</b></p>
                                    <p>size = ${variant.pool.size}</p>
                                    <p>type = ${variant.pool.type ? 'max' : 'mean'}</p>
                                </td>
                                <td>
                                    ${getMatrixElement(4, false)}
                                </td>
                                <td>
                                    ${getMatrixElement(2, true)}
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        `

        $('#jsLab').html(content)

        $('.matrix_cell').on("input", () => {
            console.log('input', this.answer)
        })

        $("#answerInput").on("input", () => {
            this.answer.result = $("#answerInput").val();
        });
    },

    getCondition: function(){},
    getResults: function(){
        function getRandomInt(max) {
          return Math.floor(Math.random() * max);
        }

        return JSON.stringify(this.answer);
    },
    calculateHandler: function(text, code){},
}

window.onload = function() {
    Vlab.init();
};
