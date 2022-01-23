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
        result: ""
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
                                <td>Входные данные</td>
                                <td>Выходные данные</td>
                            </thead>
                            <tr>
                                <td>
                                    <p>Сверточный слой</p>
                                    <p>padding = ${variant.conv.padding}</p>
                                    <p>stride = ${variant.conv.stride}</p>
                                </td>
                                <td>${ JSON.stringify(variant.input.data) }</td>
                                <td>
                                    <input placeholder="10" id="answerInput"></input>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <p>Слой пулинга</p>
                                    <p>size = ${variant.pool.size}</p>
                                    <p>type = ${variant.pool.type ? 'max' : 'mean'}</p>
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        `

        $('#jsLab').html(content)

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
