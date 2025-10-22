package school.sptech;

import school.sptech.exception.ArgumentoInvalidoException;
import school.sptech.exception.JogoInvalidoException;
import school.sptech.exception.JogoNaoEncontradoException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Empresa {
    private String nome;
    private List<Jogo> jogos;

    public Empresa(){
        this.jogos = new ArrayList<>();
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void adicionarJogo(Jogo jogo)
    throws JogoInvalidoException{
        if(jogo != null && jogo.getCodigo() != null && !jogo.getCodigo().isEmpty() && jogo.getNome() != null && !jogo.getNome().isEmpty() && jogo.getGenero() != null && !jogo.getGenero().isEmpty() && jogo.getPreco() != null && jogo.getAvaliacao() != null && jogo.getDataLancamento() != null && !jogo.getDataLancamento().isAfter(LocalDate.now()) && jogo.getPreco() > 0 && jogo.getAvaliacao() <= 5 && jogo.getAvaliacao() >= 0){
            jogos.add(jogo);
        }
        else {
            throw new JogoInvalidoException("Jogo invalido");
        }
    }
    public Jogo buscarJogoPorCodigo(String codigo)
    throws JogoInvalidoException, ArgumentoInvalidoException {
        Jogo jogoEncontrado = null;
        if (codigo == null || codigo.isEmpty() || codigo.isBlank()) {
            throw new ArgumentoInvalidoException("Codigo invalido");
        } else {
            for (Jogo j : jogos) {
                if (j.getCodigo().equals(codigo)) {
                    jogoEncontrado = j;
                    break;
                }
            }
            if(jogoEncontrado == null){
                throw new JogoNaoEncontradoException("Jogo não encontrado");
            }
        }
        return jogoEncontrado;
    }
    public void removerJogoPorCodigo(String codigo)
    throws JogoNaoEncontradoException, ArgumentoInvalidoException {
        Jogo jogoEncontrado = null;
        if (codigo == null || codigo.isEmpty() || codigo.isBlank()) {
            throw new ArgumentoInvalidoException("Codigo invalido");
        } else {
            for (Jogo j : jogos) {
                if (j.getCodigo().equals(codigo)) {
                    jogos.remove(j);
                    jogoEncontrado = j;
                    break;
                }
            }
            if(jogoEncontrado == null){
                throw new JogoNaoEncontradoException("Jogo não encontrado");
            }
        }
    }
    public Jogo buscarJogoComMelhorAvaliacao()
    throws JogoNaoEncontradoException{
        Jogo MelhorAvaliacao = null;
        for(Jogo j:jogos){
            for(Jogo o:jogos){
                if(j.getAvaliacao() > o.getAvaliacao()){
                    MelhorAvaliacao = j;
                } else if(o.getAvaliacao() > j.getAvaliacao()){
                    MelhorAvaliacao = o;
                } else if (Objects.equals(j.getAvaliacao(), o.getAvaliacao())){
                    if(j.getDataLancamento().isAfter(o.getDataLancamento())){
                        MelhorAvaliacao = j;
                    } else {
                        MelhorAvaliacao = o;
                    }
                }
            }
        }
        if(jogos.isEmpty()){
            throw new JogoNaoEncontradoException("Jogo não encontrado");
        }
        return MelhorAvaliacao;
    }
    public List<Jogo> buscarJogoPorPeriodo(LocalDate dataInicio, LocalDate dataFim)
    throws ArgumentoInvalidoException {
        List<Jogo> jogo = new ArrayList<>();
        if (dataInicio == null || dataFim == null || dataInicio.isAfter(dataFim)) {
            throw new ArgumentoInvalidoException("Datas invalidas");
        } else {
            for (Jogo j : jogos) {
                if (j.getDataLancamento().isBefore(dataFim) && j.getDataLancamento().isAfter(dataInicio)) {
                    jogo.add(j);
                }
            }
        }
        return jogo;
    }
}
