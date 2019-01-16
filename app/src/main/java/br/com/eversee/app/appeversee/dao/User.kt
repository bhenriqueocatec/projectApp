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
    var logado: Int = 0
    var pode_baixar_nc = 0
    var acessar_todos_projetos = 0
    var avaliacao_plano_acao = 0

    constructor(id: Int, usuario: String, senha: String, ativado: Int, admin: Int, system: Int,
                nome_completo: String, id_empresa: Int, id_cliente: Int, tipo_avaliacao: Int,
                logado: Int, pode_baixar_nc: Int, acessar_todos_projetos: Int, avaliacao_plano_acao: Int){
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
        this.logado = logado
        this.pode_baixar_nc = pode_baixar_nc
        this.acessar_todos_projetos = acessar_todos_projetos
        this.avaliacao_plano_acao = avaliacao_plano_acao
    }
}