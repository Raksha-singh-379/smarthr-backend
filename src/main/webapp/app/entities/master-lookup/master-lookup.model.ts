import dayjs from 'dayjs/esm';

export interface IMasterLookup {
  id: number;
  name?: string | null;
  value?: string | null;
  description?: string | null;
  type?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  companyId?: number | null;
}

export type NewMasterLookup = Omit<IMasterLookup, 'id'> & { id: null };
