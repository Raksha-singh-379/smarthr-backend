import dayjs from 'dayjs/esm';

export interface IReEnumeration {
  id: number;
  salaryBasis?: string | null;
  amount?: number | null;
  paymentType?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  employeId?: number | null;
  companyId?: number | null;
}

export type NewReEnumeration = Omit<IReEnumeration, 'id'> & { id: null };
