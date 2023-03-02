var app = new Vue({
    el: '#app',

    // data =================================
    data: {
        url: 'http://localhost:8080',
        aggregatedData: [],
        issues: [],
        newPosition: {
            code: "",
            quantity: "",
            bookValue: "",
            minimumQuantity: "",
        },
        mtmData: {
            code: "",
            marketValue: "",
        },
        result: {
            success: undefined,
            message: ""
        },
        resultOfMtm: {
            success: undefined,
            message: ""
        }
    },


    // methods ===============================
    methods: {
        renderTable: function() {
            axios
                .get(this.url+'/table')
                .then(response => {
                    var data = response.data
                    data.forEach(datum => {
                        if(!datum.marketValue) {
                            datum.marketValue='#N/A';
                            datum.profitAndLoss='#N/A';
                        }
                    })
                    this.aggregatedData = response.data
                })
                .catch((err) => {
                    alert("データの取得に失敗しました")
                    console.log(err)
                });
        },
        addPosition: function() {
            if (this.isInvalidCode) return;
            if (this.isInvalidNumberForQuantity) return;
            if (this.isInvalidNumberForBookValue) return;
            let body = {
                code: this.newPosition.code,
                quantity: this.newPosition.quantity,
                bookValue: this.newPosition.bookValue
            }
            console.log(body)

            axios
                .post(this.url+'/positions/add', body)
                .then(response => {
                    console.log(response)
                    this.renderTable()
                    this.result.message = `銘柄コード "${body.code}" を追加しました。`
                    this.result.success = true

                    this.newPosition.code = ""
                    this.newPosition.quantity = ""
                    this.newPosition.bookValue = ""
                })
                .catch((err) => {
                    console.log(err)
                    this.result.message = `追加に失敗しました`
                    this.result.success = false
                });
        },
        mtm: function() {
            if (this.isInvalidCodeForMtM) return;
            if (this.isInvalidNumberForMarketValue) return;
            let body = {
                code: this.mtmData.code,
                marketValue: this.mtmData.marketValue
            }
            console.log(body)

            axios.put(this.url+'/mtm', body)
            .then(response => {
                console.log(response)
                this.renderTable();
                this.resultOfMtm.success = true;
                this.resultOfMtm.message = `銘柄 '${body.code}' を ${body.marketValue} 円で値洗いしました`

                this.mtmData.code = ""
                this.mtmData.marketValue = ""
            })
            .catch((err) => {
                console.log(err)
                this.result.message = '追加に失敗しました'
                this.result.success = false
            })
        }
    },

    // 予め計算 ==================================
    computed: {
        isInvalidCode: function() {
            if (!this.newPosition.code) return false;
            return this.issues.filter(issue => issue.code == this.newPosition.code)==0;
        },
        isInvalidNumberForQuantity: function() {
            if (!this.newPosition.quantity) return false;
            return !this.newPosition.quantity.match(/-?[0-9]+/)
        },
        isInvalidQuantity: function() {
            let datum = this.aggregatedData.find(datum => datum.code === this.newPosition.code)
            if (!datum) return Number(this.newPosition.quantity) < 0
            this.minimumQuantity = datum.quantity
            return Number(this.minimumQuantity) + Number(this.newPosition.quantity) < 0;
        },
        isInvalidNumberForBookValue: function() {
            if (!this.newPosition.bookValue) return false;
            return !this.newPosition.bookValue.match(/[1-9][0-9]*/)
        },
        isInvalidCodeForMtM: function() {
            if (!this.mtmData.code) return false;
            return this.issues.filter(issue => issue.code == this.mtmData.code)==0;
        },
        isInvalidNumberForMarketValue: function() {
            if (!this.mtmData.marketValue) return false;
            return !this.mtmData.marketValue.match(/^[1-9][0-9]*$/)
        }
    },

    // DOMマウント後に呼び出し
    mounted() {
        this.renderTable();
    },

    // Vueインスタンス生成時に呼び出し
    created() {
        axios
            .get(this.url+'/issues')
            .then(response => { this.issues = response.data })
            .catch((err) => { console.log(err) })
    },
});
