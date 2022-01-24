function getEmptyMatrix(size) {
    return Array(size).fill().map(
        ()=>Array(size).fill()
    )
}

Vue.component('matrix', {
    props: {
        size: Number,
        editable: { type: Boolean, default: false },
        fill: Array
    },
    data: function() {
        return {
            matrix: this.fill || getEmptyMatrix(this.size)
        }
    },
    template: `
        <table class="matrix">
            <tr v-for="(n, i) in size">
                <td v-for="(m, j) in size" class="matrix_cell">
                    <input v-if="editable" v-model.number="matrix[i][j]" type="number" />
                    <span v-else>{{ matrix[i][j] == null ? "" : matrix[i][j] }}</span>
                </td>
            </tr>
        </table>
    `
});

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
            size: 2
        }
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

    //Инициализация ВЛ
    init : function(labScope){
        let variant = this.setVariant($("#preGeneratedCode").val());

        this.app = new Vue({
            el: '#jsLab',
            data: {
                variant: variant,
                answer: {
                    convResult: getEmptyMatrix(4),
                    poolResult: getEmptyMatrix(2)
                },
            },
            created() {
                console.log('init.variant', this.variant);
            },

            template: `
                <div id="jsLab">
                <table class="table table-bordered">
                    <tr>
                        <td class="d-flex justify-content-between">
                            <h2>CNN</h2>
                            <button type="button" class="btn btn-info" id="infoModalOpener">Справка</button>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table class="table table-bordered" id="cnn-task">
                                <thead>
                                    <td>Слой</td>
                                    <td>Входные данные слоя</td>
                                    <td>Выходные данные слоя</td>
                                </thead>
                                <tr>
                                    <td>
                                        <p><b>Сверточный слой</b></p>
                                        <p>padding = {{variant.conv.padding}}</p>
                                        <p>stride = {{variant.conv.stride}}</p>
                                        <p>kernel</p>
                                        <p><matrix :size="variant.conv.kernel.length" :editable="false" :fill="variant.conv.kernel" /></p>
                                    </td>
                                    <td>
                                        <matrix :size="variant.input.data.length" :editable="false" :fill="variant.input.data" />
                                    </td>
                                    <td>
                                        <matrix :size="4" editable :fill="answer.convResult" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <p><b>Слой пулинга</b></p>
                                        <p>size = {{variant.pool.size}}</p>
                                    </td>
                                    <td>
                                        <matrix :size="4" :editable="false" :fill="answer.convResult" />
                                    </td>
                                    <td>
                                        <matrix :size="2" :editable="true" :fill="answer.poolResult" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                </div>
            `
        })
    },

    getResults: function(){
        console.log(this.app.answer);
        return JSON.stringify(this.app.answer);
    },

    setMode: function(str){},
    getCondition: function(){},
    calculateHandler: function(text, code){},
}
