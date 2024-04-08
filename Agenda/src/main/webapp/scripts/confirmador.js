/**
 * confirmação de exclusão de um contato
 * @author Samuel Urbano
 */
function confirmar(idcon){
	let resposta = confirm("Deseja realmente excluir este contato?")
	if(resposta===true){
		//alert(idcon)
		window.location.href="delete?idcon="+idcon
	}
}