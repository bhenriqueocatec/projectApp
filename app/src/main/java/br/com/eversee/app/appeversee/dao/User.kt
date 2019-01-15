package br.com.eversee.app.appeversee.dao

class UserLogin{
    var id: Int = 0
    var usuario: String = ""
    var senha: String = ""
    var ativado: Int = 0
    var admin: Int = 0
    var system: Int = 0
    var nome_completo: String = ""
    var id_empresa: Int = 0
    var id_cliente: Int = 0
    var tipo_avaliacao: Int = 0

    constructor(id: Int, usuario: String, senha: String, ativado: Int, admin: Int, system: Int,
                nome_completo: String, id_empresa: Int, id_cliente: Int, tipo_avaliacao: Int){
        this.id = id
        this.usuario = usuario
        this.senha = senha
        this.ativado = ativado
        this.admin = admin
        this.system = system
        this.nome_completo = nome_completo
        this.id_empresa = id_empresa
        this.id_cliente = id_cliente
        this.tipo_avaliacao = tipo_avaliacao
    }
}