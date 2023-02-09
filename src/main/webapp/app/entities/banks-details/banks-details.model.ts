import dayjs from 'dayjs/esm';

export interface IBanksDetails {
  id: number;
  accountNumber?: number | null;
  bankName?: string | null;
  branchTransCode?: string | null;
  taxNumber?: string | null;
  branchName?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  refTableType?: string | null;
  refTableId?: number | null;
  companyId?: number | null;
}

export type NewBanksDetails = Omit<IBanksDetails, 'id'> & { id: null };
