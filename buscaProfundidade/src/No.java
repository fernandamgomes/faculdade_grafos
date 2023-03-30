public class No {
    protected int tempoDescoberta;
    protected int tempoTermino;
    protected int idVertice;

    public int getIdVertice() {
        return idVertice;
    }

    public int getTempoDescoberta() {
        return tempoDescoberta;
    }

    public int getTempoTermino() {
        return tempoTermino;
    }

    public void setIdVertice(int idVertice) {
        this.idVertice = idVertice;
    }

    public void setTempoDescoberta(int tempoDescoberta) {
        this.tempoDescoberta = tempoDescoberta;
    }

    public void setTempoTermino(int tempoTermino) {
        this.tempoTermino = tempoTermino;
    }

    public No (int id, int tt, int td) {
        setIdVertice(id);
        setTempoDescoberta(td);
        setTempoTermino(tt);
    }

    public No(int idVertice) {
        setIdVertice(idVertice);
        setTempoDescoberta(0);
        setTempoTermino(0);
    }
}
