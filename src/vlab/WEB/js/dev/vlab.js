var Vlab = {

    byDefault: {
        inputMatrix: "[[1,2],[3,4]]",
        inputMatrixSize: 2,
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

        const text = "Текст задания";
        const text_info = "Текст подсказки";
        const table = `
            <h1>Контент задания</h1>
            <p>Размер входной матрицы: ${variant.inputMatrixSize}</p>
            <p>Матрица: ${variant.inputMatrix}</p>
        `;

        const content = text + "<br>" +
            table + "<br>" +
            '<input placeholder="10" id="answerInput"></input>' +
            text_info + "<br>";

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
